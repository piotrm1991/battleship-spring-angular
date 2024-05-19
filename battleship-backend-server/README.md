# Project Battleship Game

Battleship Game based on websockets. App allows to communicate with other player on global chat 
or on private chat during the game.

## Environment:

```
Language:   Java 17
DB:         MySQL 8.0
Deploy:     Docker 20.10.17
```

## Build application

### Backend

1. Create `.env.local` based on `example.env.dist` file and fill it with variables' values (you can leave it as is for local development purposes as it already contains some example values).

2. Build the Docker image for your application by running the following command in project directory:
> cd battleship-backend-server
> 
> docker-compose up -d --build

Note: after the first execution you can simply launch db with
> docker-compose up -d

This will start database in separate docker container.

Run Application with Intellij IDEA functionality, edit configuration - set active profile to local.

Add VM option:
> -Dspring.profiles.active=local

Project should start as expected

### Frontend

In progress...

## Postman

1. Import all 3 collections to Postman.

2. Create collection for websockets, both for global chat and lobby.

3. Create to requests for each collection.

4. Set addresses for them.

> Global chat: `{{LOCAL_WS}}/api/chat`
>
> Lobby: `{{LOCAL_WS}}/api/lobby`

5. Add authorization header:

> `Authorization` `Bearer {{ACCESS_TOKEN}}`

6. Accepted message formats:

> UserMessage record

7. You can test websocket connection by altering between requests and environments.

## StyleGuides

To run style check it is needed to execute `mvn checkstyle:checkstyle`

You can also install `checkstyle` plugin from Intellij IDEA marketplace. 