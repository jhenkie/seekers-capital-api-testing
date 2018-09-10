package common;

import java.util.ArrayList;

public class SupportingData {
    public static String data = "data";
    public static String id = "id";

    public static class StatusCode {
        public static int success = 200;
        public static int notFound = 404;
        public static int noContent = 204;
        public static int badGateway = 502;
    }

    public static class User {
        public static String baseURI = "http://users.local";
        public static String nameAttribute = "name";
        public static String lastNameAttribute = "lastname";
        public static String nameText = "Johannes";
        public static String lastNameText = "Henkie";

        public static class Attribute {
            public static String nameAttribute = "data.name";
        }

        public static String idTextRandom = "xxx";
    }

    public static class Movie {
        public static String baseURI = "http://movies.local";
        public static String titleAttribute = "title";
        public static String directorAttribute = "director";
        public static String ratingAttribute = "rating";
        public static String titleText = "Learn Automate API";
        public static String directorText = "Johannes";
        public static int ratingText = 9;

        public static class Attribute {
            public static String director = "data.director";
            public static String id = "data.id";
            public static String title = "data.title";
            public static String rating = "data.rating";
        }

        public static String idTextRandom = "xxx";
    }

    public static class Showtimes {
        public static String baseURI = "http://showtimes.local";
        public static String dateAttribute = "date";
        public static String moviesAttribute = "movies";
        public static String dateText = "2018-08-08";
        public static String[] moviesText = new String[]{"1234567890", "0987654321"};

        public static class Attribute {
            public static String id = "data.id";
            public static String date = "data.date";
            public static String movies = "data.movies";
        }

        public static String idTextRandom = "xxx";
    }

    public static class Booking {
        public static String baseURI = "http://bookings.local";
        public static String useridAttribute = "userid";
        public static String showtimeidAttribute = "showtimeid";
        public static String moviesAttribute = "movies";
        public static String useridText = "1234567890";
        public static String showtimeidText = "0987654321";
        public static String[] moviesText = new String[]{"1234567890", "0987654321"};

        public static class Attribute {
            public static String userid = "data.userid";
            public static String showid = "data.showtimeid";
            public static String movies = "data.movies";
        }

        public static String idTextRandom = "xxx";
    }

}
