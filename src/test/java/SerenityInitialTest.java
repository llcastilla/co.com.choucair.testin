import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsIterableContaining.hasItems;

@RunWith(SerenityRunner.class)

public class SerenityInitialTest {
    private static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void getUser() {
        Actor leonardo = Actor.named("vamos a probar la api")
                .whoCan(CallAnApi.at(restApiUrl));


        leonardo.attemptsTo(

                GetUser.fromPage(2)
        );

        assertThat(SerenityRest
                .lastResponse()
                .statusCode())
                .isEqualTo(200);


        leonardo.should(
                seeThatResponse("all the expected users should be returned",
                        response -> response.statusCode(200)
                                .body("data.first_name", hasItems("Eve", "Charles", "Tracey")))
        );
    }


}


