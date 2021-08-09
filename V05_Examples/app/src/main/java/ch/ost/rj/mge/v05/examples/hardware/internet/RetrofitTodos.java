package ch.ost.rj.mge.v05.examples.hardware.internet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitTodos {
    @GET("todos")
    Call<List<TodoItem>> getItems();
}
