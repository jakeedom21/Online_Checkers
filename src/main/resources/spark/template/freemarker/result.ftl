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
      
      <#if won>
        <#if oppoForfeit??>
        <p>Your opponent has resigned!</p>
        </#if>
        <p>You Won!</p>
      <#else>
        <p>You Lost!</p>
      </#if>

    </div>
    
  </div>
</body>
</html>
