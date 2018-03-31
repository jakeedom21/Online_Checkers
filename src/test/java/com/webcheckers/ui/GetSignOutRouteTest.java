package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

class GetSignOutRouteTest {

  // mock objects
  private GetSignOutRoute getSignOutRoute;
  private Request request;
  private Response response;
  private Session session;
  private PlayerLobby playerLobby;


  @BeforeEach
  void setUp() {
    request = mock(Request.class);
    response = mock(Response.class);
    session = mock(Session.class);
    playerLobby = mock(PlayerLobby.class);

    playerLobby.addPlayer("player1");
    when(request.session()).thenReturn(session);
    when(session.attribute(Constants.PLAYER_NAME)).thenReturn("player1");

    getSignOutRoute = new GetSignOutRoute(playerLobby);
  }


  @Test
  public void test_signout() {
    // Invoke the test
    getSignOutRoute.handle(request, response);


    assertEquals(null, session.attribute(Constants.SIGNED_IN_PLAYER));
    assertEquals(false, playerLobby.getPlayers().contains("player1"));
    assertEquals(false, playerLobby.hasUserName("player1"));
  }
}