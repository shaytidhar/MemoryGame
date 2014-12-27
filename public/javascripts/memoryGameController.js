"use strict";

myApp.controller("memoryGameController", ['$scope',
                                          '$http', 
                                          '$location',
                                          '$rootScope',
                function($scope,
                         $http,
                         $location,
                         $rootScope) {
        
        // Init controller
        $scope.init = function () {
            
            $scope.players = [
                {"name":"Shay"},
                {"name":"Moshe"}
            ];
            $scope.numOfRepeats = 2;  
            $scope.numOfCards = 10;  
            $scope.playerToAddName = "";        
        };

        // Add player
        $scope.addPlayer = function () {
            
            // Set new player
            var newPlayer = {};
            newPlayer.name = $scope.playerToAddName;
            
            // Add player
            $scope.players[$scope.players.length] = newPlayer;
        };

        // Delete all players
        $scope.deletePlayers = function () {
            $scope.players = [];
        };
                    
        // Staer new game
        $scope.startNewGame = function()
        {
            $http.post("/startGame",
                       {
                            players: $scope.players,
                            numOfCards: $scope.numOfCards,
                            numOfRepeats: $scope.numOfRepeats
                        })
                .success(function(response) {

                    alertify.success("המשחק נוצר בהצלחה!");    

                    // Go to board
                    $location.path("/Go");
                })
                .error(function(resopnse) {
                    alertify.error("התרחשה שגיאה בטעינת הלוח");
                });  
        };
                    
        $scope.init();
}]);