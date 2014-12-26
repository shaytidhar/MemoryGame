"use strict";

myApp.directive('memoryGameBoard', ['$http',
  function($http) {
  
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
                        $scope.updateBoardCards(response.crdChangedCards);            
                    });  
            };
            
            // Turn a card
            $scope.turnCard = function(nCardIndex)
            {
                
                // Play turn
                $http.post("/playTurn", 
                            { 
                                nChosenCard: nCardIndex
                            }
                          )
                    .success(function(response) {
                        
                        // Update the board
                        $scope.updateBoardCards(response.crdChangedCards); 
                    });
            };
            
            // Update the boead by the given changed cards
            $scope.updateBoardCards = function(hmChangedCards)
            {
                  
                // Run through changed cards
                $.each(hmChangedCards, function (key, crdCurrCard) {

                    // Set changed card
                    $scope.cards[key - 1] = crdCurrCard;
                });                    
            }
            
            // Initialize the board
            $scope.initBoard();
        }
    };
}]);