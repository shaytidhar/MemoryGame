"use strict";

myApp.directive('memoryGameBoard', ['$http',
                                    '$timeout',
                                    '$interval',
                                    '$location',
  function($http,
           $timeout,
           $interval,
           $location) {
  
    return {
    
        restrict: 'E',
        templateUrl: './javascripts/memoryGameBoard/memoryGameBoard.html',
        controller: function($scope, $http) {
                        
            // Initialize the game board
            $scope.initBoard = function()
            {
                // Initial cards
                $scope.cards = [];
                
                // Initial players
                $scope.players = [];
                               
                $scope.waitForMyTurn();
            };
                        
            // Turn a card
            $scope.flipCard = function(crdChosenCard)
            {
                // If already turned
                if (crdChosenCard.card.status === "TURNED")
                {
                    alertify.error("הקלף כבר נבחר, בחר קלף אחר");
                }
                // Exposed
                else if (crdChosenCard.card.status === "EXPOSED")
                {
                    alertify.error("הקלף כבר נחשף, בחר קלף אחר");
                }
                // Upside down
                else if (crdChosenCard.card.status === "UPSIDE_DOWN")
                {                
                    // Flip the card
                    $scope.flipCardHandler(crdChosenCard);
                }
            };
            
            // Flip the card
            $scope.flipCardHandler = function(crdChosenCard) {
              
                $http.post("/flipCard", 
                            { 
                                nChosenCard: crdChosenCard.$index,
                                strUserName: $scope.userName
                            })
                .success(function(response) {
                   
                    // Flip the card
                    crdChosenCard.card = response;

                    // Wait a second              
                    $timeout(function(){
                        
                        // Get the flip result
                        $scope.getFlipResult();
                    }, 2000);
                })
                .error(function(resopnse) {
                        alertify.error("סבלנות");
                });
            };
            
            // Check the board state
            $scope.getFlipResult = function(){
                                
                $http.get("/getFlipResult")
                    .success(function(response){
                    
                        // If game not set
                        if (response === "") {

                            alertify.error("המשחק לא מאותחל");
                            
                            // Go to settings
                            $location.path("/Set");
                        }
                        else {
                            
                            // Handle the game result
                            $scope.gameResultHandler(response);
                        }
                    })
                    .error(function(resopnse) {
                        alertify.error("התרחשה שגיאה");
                    });
            };
            
            // Handle the game result
            $scope.gameResultHandler = function(response)
            {                    
                
                // Update the board
                $scope.updateBoardCards(response.changedCards); 

                // Set current user
                $scope.currentPlayer = response.currentPlayer;                 

                // Set players
                $scope.updatePlayers(response.changedPlayers);

                // Check game status
                $scope.checkGameStatus(response.gameStatus, response.currentPlayer);
            };
            
            // Update the board by the given changed cards
            $scope.updateBoardCards = function(hmChangedCards)
            {
                if (hmChangedCards != null) {
                    
                    // Run through changed cards
                    $.each(hmChangedCards, function (key, crdCurrCard) {

                        // Set changed card
                        $scope.cards[key] = crdCurrCard;
                    });                    
                }
            };
            
            // Update the players list
            $scope.updatePlayers = function(hmPlayers)
            {
                if (hmPlayers != null) {
                    // Run through changed cards
                    $.  each(hmPlayers, function (key, crdCurrPlayer) {

                        // Set changed card
                        $scope.players[key] = crdCurrPlayer;
                    });                    
                }
            };
            
            // Check the turn status and act accordinaly
            $scope.checkGameStatus = function(turnStatus, currentPlayer) {
                                
                debugger;
                // Over
                if (turnStatus === "OVER")
                {
                    // If current user
                    if (currentPlayer.name === $scope.userName){
                        alertify.success("ניצחת!");
                    }
                    else {
                        alertify.error("הפסדת!");
                    }
                }
                // In Game
                else {
                    
                    // If turn pass
                    if (currentPlayer.name != $scope.userName) {
                        // Go to wait mode
                        $scope.waitForMyTurn();                   
                    }
                }
            };
            
            // Refresh game status till it's my turn
            $scope.waitForMyTurn = function () {

                // Not my turn
                $scope.myTurnClass = "disabledBoard";
                
                // Get game status
                var stop = $interval(function() {
                    $http.get("/getGameStatus")
                        .success(function(response) {
                        
                        // If game not set
                        if (response === "") {

                            alertify.error("המשחק לא מאותחל");
                            
                            $interval.cancel(stop);
                            
                            // Go to settings
                            $location.path("/Set");
                        }
                        else {
                          
                            // Handle the game result
                            $scope.gameResultHandler(response);

                            // If it's my turn
                            if ($scope.userName === response.currentPlayer.name)
                            {
                                $interval.cancel(stop);

                                // My turn!
                                $scope.myTurnClass = "";
                            }
                        }
                    })
                    .error(function(resopnse) {
                        alertify.error("התרחשה שגיאה ברענון הלוח");
                    });  
                }, 2000);
            };
            
            // Return card's class by its status
            $scope.getCardClass = function(strChosenCardStatus) {
                var strCardClass = "";
                
                // If upside down
                if (strChosenCardStatus === "UPSIDE_DOWN")
                {
                    strCardClass = "btn-primary";
                }
                // If turned
                else if (strChosenCardStatus === "TURNED")
                {
                    strCardClass = "btn-default";
                }
                // If exposed
                else if (strChosenCardStatus === "EXPOSED")
                {
                    strCardClass = "btn-success";
                }
                
                return (strCardClass);
            };
            
            // Return player row's class by current
            $scope.getPlayerRowClass = function(playerRow) {
                if ($scope.currentPlayer != null)
                {
                    return (playerRow.player.id === $scope.currentPlayer.id ? "success" : "");
                }
            }            
            
            // Check if card should be disabled
            $scope.isDisable = function(strChosenCardStatus) {
                
                return (strChosenCardStatus != "UPSIDE_DOWN");
            };
            
            $scope.initBoard();
        }
    };
}]);