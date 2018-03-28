<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <#--<meta http-equiv="refresh" content="10">-->
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    <div class="navigation">
    <#if signedInPlayer>
      <a href="/">my home</a> |
    </#if>
      <a href="/rules">rules</a> |
    <#if signedInPlayer>
      <a href="/signout">sign out [${playerName}]</a>
    <#else>
      <a href="/signin">sign in</a>
    </#if>
    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <p>Online: ${numUsers}</p>

      <#if signedInPlayer>
        <#if busyOpponentError>
          <div class="error">The player you chose is currently in a game! Choose another one.</div>
        </#if>

        <#list freePlayers as opponent>
          <form action="./game" method="GET">
            <input type="hidden" name="opponentName" value="${opponent}" />
            <input type="submit" value="Challenge ${opponent} to a game!" />
      	  </form>
      	<#else>
          <p>Waiting for an opponent...</p>
      	</#list>
      </#if>
    </div>
  </div>
</body>
</html>
