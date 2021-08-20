# game-api

game-api is a REST web server , accomodating the palindrome game .Services include user registration , utilites for creating and validating authorization tokens , as well as the Play and retrieve leaderboards functionality .  

## Installation
- Clone the repository , and cd to the project location :

```bash
cd /projectLocation
```
- Type :
```bash
docker-compose up
```
- Test it with one of the below CURLS !
## How to use :
Sample Curls for the APIs :

```bash

#   Register User
  curl --location --request POST 'localhost:8080/register' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "passWord": "password",
  "userName":"username"
  }'

#  Get authorization token
  curl --location --request POST 'localhost:8080/auth/token' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "passWord": "password",
  "userName":"username"
  }'
  
#  Play
  curl --location --request POST 'localhost:8080/game/palindrome' \
  --header 'authorizationToken: U0lHTklOR19LRVkxMTIyMzNQcm9jb3Bpcw==' \
  --header 'username: username' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "palindromeText" : "dsfadsfsd"
  }'
  
  #  Get top 10 leader board
    curl --location --request GET 'localhost:8080/leaderBoard/all' \
  --header 'authorizationToken: U0lHTklOR19LRVkxMjM0NTZBbGV4YW5kcm9z' \
  --header 'username: username'
    
    #  Get user leader board
    curl --location --request GET 'localhost:8080/leaderBoard/player' \
  --header 'authorizationToken: U0lHTklOR19LRVkxMjM0NTZBbGV4YW5kcm9z' \
  --header 'username: username'
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
