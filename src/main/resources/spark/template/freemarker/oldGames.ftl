<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Replay Games | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>Web Checkers</h1>
    <div class="navigation">
        <a href="/">home</a>
    </div>
    <div class="body">

        <h2>Archived Games for ${playerName} </h2>
        <#list oldOpponentGames as oldGame>
          <form action="./replay" method="GET">
              <input type="hidden" name="oldGameId" value="${oldGame?index}" />
              <input type="submit" value="View replay of game ${oldGame?index} with ${oldGame}." />
          </form>
        <#else>
          <p>Waiting for an opponent...</p>
        </#list>
    </div>
</div>
</body>
</html>
