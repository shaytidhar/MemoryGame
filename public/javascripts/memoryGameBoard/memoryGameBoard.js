"use strict";

myApp.directive('memoryGameBoard', ['$http',
                                    '$timeout',
  function($http,
           $timeout) {
  
    return {
    
        restrict: 'E',
        templateUrl: './javascripts/memoryGameBoard/memoryGameBoard.html',
        controller: function($scope, $http) {
            
            // Initialize the game board
            $scope.initBoard = function()
            {
                // Initial cards
                $scope.cards = [];
                
                $http.post("/startGame")
                    .success(function(response) {
               
                        // Update the board
                        $scope.updateBoardCards(response.changedCards);            
                    })
                    .error(function(resopnse) {
                            alertify.error("התרחשה שגיאה בטעינת הלוח");
                        }                        
                    );  
            };
            
            // Turn a card
            $scope.turnCard = function(crdChosenCard)
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
                    // Play turn
                    $scope.playTurn(crdChosenCard);
                }
            };
            
            // Play turn
            $scope.playTurn = function(crdChosenCard) {
                $http.post("/flipCard", 
                            { 
                                nChosenCard: crdChosenCard.$index
                            })
                .success(function(response) {
                   
                    // Flip the card
                    crdChosenCard.card = response;

                    // Wait a second                    
                    $timeout(function(){
                        
                        // Check status
                        $scope.getBoardStatus();
                    }, 1000);
                })
                .error(function(resopnse) {
                        alertify.error("סבלנות");
                    }                        
                );
            };
            
            // Check the board state
            $scope.getBoardStatus = function(){
                
                $http.get("/getBoardState")
                    .success(function(response){
                    
                        $scope.checkTurnStatus(response.boardStatus);
                    
                        // Update the board
                        $scope.updateBoardCards(response.changedCards);    
                    })
                    .error(function(resopnse) {
                        alertify.error("התרחשה שגיאה");
                    });
            };
            
            // Update the board by the given changed cards
            $scope.updateBoardCards = function(hmChangedCards)
            {
                // Run through changed cards
                $.each(hmChangedCards, function (key, crdCurrCard) {

                    // Set changed card
                    $scope.cards[key] = crdCurrCard;
                });                    
            };
            
            // Check the turn status and act accordinaly
            $scope.checkTurnStatus = function(turnStatus)
            {
                // Won
                if (turnStatus === "WON")
                {
                    alertify.success("המשחק נגמר");
                }
                // Wrong
                else if (turnStatus === "WORONG")
                
                    $http.get("/getRefreshedBoard");
                }
            };
            
            // Return card's class by its status
            $scope.getCardClass = function(strChosenCardStatus)
            {
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
            }
            
            // Check if card should be disabled
            $scope.isDisable = function(strChosenCardStatus)
            {
                return (strChosenCardStatus != "UPSIDE_DOWN");
            }
            
            // Initialize the board
            $scope.initBoard();
        }
    };
}]);