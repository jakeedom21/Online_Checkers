package com.webcheckers.ui;

import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Created by Brandon Dossantos <bxd7887@rit.edu>
 */
@Tag("UI-tier")
public class GetResultRouteTest {
    // component under test
    private GetResultRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;

    /**
     * Set up mock objects to test
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        when(session.attribute(Constants.OPPONENT_FORFEIT)).thenReturn(true);
        when(session.attribute(Constants.GAME_WON)).thenReturn(true);

        CuT = new GetResultRoute(engine);
    }

    /**
     * Test that the result page has the correct values
     */
    @Test
    public void render_result_page() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(Constants.OPPONENT_FORFEIT, true);
        testHelper.assertViewModelAttribute(Constants.GAME_WON, true);
        //   * test view name
        testHelper.assertViewName(GetResultRoute.VIEW_NAME);

    }
}
