package com.example.powerincode.popularmovies.utils;

import com.example.powerincode.popularmovies.PopularMovieApplication;
import com.example.powerincode.popularmovies.network.models.Genre;
import com.example.powerincode.popularmovies.network.models.GenreList;

import java.util.List;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public class GenreUtils {
    public static final GenreUtils shared = new GenreUtils();

    public String getGenres(List<Integer> genreIds, int max) {
        String result = "";

        int i = 0;
        for (Integer genreId : genreIds) {
            for (Genre genre : PopularMovieApplication.sGenres) {
                if(!genre.id.equals(genreId)) {
                    continue;
                }

                i++;

                if (result.isEmpty()) {
                    result = genre.name;
                } else {
                    result += ", " + genre.name;
                }

                if(i == max) {
                    return result;
                }
            }
        }

        return result;
    }

    public List<Genre> getGenres() {
        return PopularMovieApplication.sGenres;
    }
}
