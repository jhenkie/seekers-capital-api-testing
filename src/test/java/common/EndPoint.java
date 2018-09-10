package common;

public interface EndPoint {
    String GET_USER = "/users";
    String GET_USER_WRONG = "/user";
    String USER_DELETE = "/users/{id}";
    String GET_MOVIE = "/movies";
    String GET_MOVIE_WRONG = "/movie";
    String MOVIE_WITH_ID = "/movies/{id}";
    String GET_SHOWTIMES = "/showtimes";
    String GET_SHOWTIMES_WRONG = "/showtime";
    String SHOWTIMES_WITH_ID = "/showtimes/{id}";
    String GET_BOOKINGS = "/bookings";
    String GET_BOOKINGS_WRONG = "/booking";
    String BOOKINGS_WITH_ID = "/bookings/{id}";
}
