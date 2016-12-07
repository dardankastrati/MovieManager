
package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.service.Services;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dardan.kastrati
 */
@Named("movieBean")
@ViewScoped
public class MovieBean implements Serializable {
    @Inject
    Services service;

    public MovieBean(){
        
    }

    public List<Movie> getMoviesList(){
        return service.getMoviesList();
    }
    
//    public String nav(){
//        return "addMovie";
//    }
}
