package br.com.vini.task.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI  = "http://localhost:8888/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured
			.given()
			.when ()
				.get("/todo")
			.then()
				.statusCode(200)
				.log().all();
	}
	
	@Test
	public void deveSalvarTarefas() {
		RestAssured
			.given()
				.body("{ \"task\":\"Teste via api\", \"dueDate\":\"2021-05-04\" }")
				.contentType(ContentType.JSON)
			.when ()
				.post("/todo")
			.then()
				.statusCode(201)
				.log().all();
	}
	
	@Test
	public void naoDeveAdicionarTarefasInvalida() {
		RestAssured
			.given()
				.body("{ \"task\":\"Teste via api\", \"dueDate\":\"2019-05-04\" }")
				.contentType(ContentType.JSON)
			.when ()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"))
				.log().all();
	}
	
	@Test
	public void DeveRemoverTarefaComSucesso() {
		Integer id = RestAssured
		.given()
			.body("{ \"task\":\"Teste de remoção via api\", \"dueDate\":\"2021-05-04\" }")
			.contentType(ContentType.JSON)
		.when ()
			.post("/todo")
		.then()
			.statusCode(201)
			.extract().path("id");
		
		System.out.println(id);
		
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204);
		
	}
}







