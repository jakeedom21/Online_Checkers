<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>Result</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
    <h1>Game Result</h1>
    
    <div class="navigation">
      <a href="/">new game</a>
      <#-----<a href="/">replay</a>---->
      <a href="/signout">sign out [${playerName}]</a>
    </div>
    
    <div class="body">
      
      <#if Won>
        <#if opponentForfeit??>
        <h2>Your opponent has resigned!</h2>
        </#if>
        <h2>You Won!</h2>
      <#else>
        <h2>You Lost!</h2>
      </#if>

    </div>
    
  </div>
</body>
</html>
