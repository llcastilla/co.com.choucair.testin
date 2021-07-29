package runner;

import model.Datum;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestions;
import questions.ResponseCode;
import tasks.GetUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
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
                seeThat("all the expected users should be returned",
                        ResponseCode.was(), equalTo(200)
                ));


        Datum user = new GetUsersQuestions()
                .answeredBy(leonardo)
                .getData()
                .stream()
                .filter(x -> x.getId()==1)
                .findFirst()
                .orElse(null);
        leonardo.should(seeThat("Usuario no es nulo",
                act -> user,
                notNullValue()));
    }




}


