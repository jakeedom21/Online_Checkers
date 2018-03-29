package com.webcheckers.ui;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetHomeRoute} component.
 *
 * @author Sameen Luo <xxl2398@rit.edu> on 3/20/2018.
 */
@Tag("UI-tier")
public class GetHomeRouteTester {

    /**
     * The component-under-test (CuT).
     */
    private GetHomeRoute CuT;

    // mock objects
    private PlayerLobby playerlobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerlobby = mock(PlayerLobby.class);

        Set<String> somenames = new HashSet<>();
        somenames.add("arrr");
        somenames.add("ruff");
        somenames.add("meow");

        when(playerlobby.getPlayers()).thenReturn(somenames);

        // create a unique CuT for each test
        // the engine mock will need configuration
        CuT = new GetHomeRoute(playerlobby, engine);
    }

    /**
     * Test that CuT shows the Home view when the session is brand new.
    */
    @Test
    public void new_default_session() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(Constants.SIGNED_IN_PLAYER, Boolean.FALSE);
        testHelper.assertViewModelAttribute(Constants.TITLE, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(Constants.NUM_USER, 3);
        testHelper.assertViewModelAttribute(Constants.BUSY_OPPONENT_ERROR, null);


    }
//    public void signed_in_session(){
//        final TemplateEngineTester testHelper = new TemplateEngineTester();
//        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
//    }



}
