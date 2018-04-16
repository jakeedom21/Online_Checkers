<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/game.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script data-main="js/game/main" src="js/require.js"></script>
    <script>
        window.gameState = {
            "currentPlayer" : "${currentPlayerName}",
            "redPlayer" : "${redPlayerName}",
            "whitePlayer" : "${whitePlayerName}",
            "activeColor" : "${activeColor}",
            "viewMode": "PLAY"
        };
    </script>
</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    
    <div class="navigation">
    <#if signedInPlayer>
      <a href="/">my home</a> |
    </#if>
      <a href="#rules" id="showRules">rules</a> |
    <#if signedInPlayer>
      <a href="/signout">sign out [${playerName}]</a>
    <#else>
      <a href="/signin">sign in</a>
    </#if>
    </div>
    
    <div class="body">


      <p id="help_text"></p>
      
      <div>

        <div id="rulesModal" class="modal">
          <!-- Modal content -->
          <div class="modal-content">
            <div class="modal-header">
              <span class="close">&times;</span>
              <h1>American Rules for Checkers</h1>
            </div>
            <div class="modal-body">
              <#include "rules_body.ftl">
            </div>
          </div>

        </div>

        <div id="replayModal" class="modal">
            <div class="modal-content">
                <form>
                    <input type="number" name="replayValue" />
                    <input type="submit" />
                </form>
            </div>
        </div>
        <div id="game-controls">
        
          <fieldset id="game-info">
            <legend>Info</legend>
            
            <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
            <#else>
            <div id="message" class="info" style="display:none">
              <!-- keep here for client-side messages -->
            </div>
            </#if>
            
            <div>
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">RED</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">WHITE</td>
                </tr>
              </table>
            </div>
          </fieldset>

          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
          </fieldset>
          
        </div>
  
        <div class="game-board">
          <table id="game-board">
            <tbody>
            <#list board as row>
              <tr data-row="${row?index}">
              <#list row as space>
                <td data-cell="${space?index}"
                    <#if space.isValid() >
                    class="Space"
                    </#if>
                    >
                <#if space.hasPiece()>
                  <div class="Piece"
                       id="piece-${row?index}-${space?index}"
                       data-type="${space.getPiece().getType()}"
                       data-color="${space.getPiece().getColor()}">
                  </div>
                </#if>
                </td>
              </#list>
              </tr>
            </#list>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>
  <script data-main="js/game/index" src="js/require.js"></script>
  <script data-main="js/game/index" src="js/game/main.js"></script>

  <script type="text/javascript">
    $(document).ready(function() {
      var modalElem = document.getElementById('rulesModal');
      var modal = $(modalElem);

      $('#showRules').on('click', function() {
        modal.css('display', 'block');
      });

      $('.close').on('click', function() {
        modal.css('display', 'none');
      });

      window.onclick = function(event) {
        if (event.target === modalElem) {
          modal.css('display', 'none');
        }
      };

      $('#replayModal').onsubmit = function() {
          $('#replayModal').css('display', 'none');
          window.location = '/game';
      }

    })
  </script>
</body>
</html>
