package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.exception.NullParameterException;
import ch.hearc.ig.odi.moviemanager.service.Services;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Long currentMovieID;
    private Movie currentMovie;

    /**
     * Constructeur : on initialise le film courrant pour éviter un NullPointerException
     */
    public MovieBean() {        
        if(currentMovie == null){
            currentMovie = new Movie();
        }
    }

    /**
     * Getter on retourne l'ID du film courrant
     * @return ID du film courrant
     */
    public Long getCurrentMovieID() {
        return currentMovieID;
    }

    /**
     * Setter indique l'id du film courrant et on initialise le film par rapport à l'ID
     * @param currentMovieID ID du film
     */
    public void setCurrentMovieID(Long currentMovieID) {
        this.currentMovieID = currentMovieID;
        currentMovie = service.getMovieWithId(currentMovieID);
    }

    /**
     * Getter on rettoure le film courrant
     * @return Film courrant
     */
    public Movie getCurrentMovie() {
        return currentMovie;
    }

    /**
     * Setter on indique le film courrant
     * @param currentMovie Film courrant
     */
    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    /**
     * On retourne la liste des films
     * @return List de films
     */
    public List<Movie> getMoviesList() {
        return service.getMoviesList();
    }

    /**
     * On va parcourir toutes les personnes de la liste et compter le nombre de fois que le film courrant apparaît
     * @param movie Film concerné
     * @return Nombre entier indiquant le nombre de fois que le film a été vue
     */
    public int countPeopleOfMovie(Movie movie) {
        int cpt = 0;

        for (Person tmpPerson : service.getPeopleList()) {
            if (tmpPerson.getMovies().indexOf(movie) >= 0) {
                cpt++;
            }
        }

        return cpt;
    }

    /**
     * On retourne la liste des personnes ayant vue le film
     * @return List de personnes
     */
    public List<Person> getPersonByMovie() {
        List<Person> listCompletPerson = service.getPeopleList();
        List<Person> listPersonForMovie = new ArrayList<>();

        for (Person currentPerson : listCompletPerson) {
            if (currentPerson.getMovies().indexOf(currentMovie) != -1) {
                listPersonForMovie.add(currentPerson);
            }
        }

        return listPersonForMovie;
    }

    /**
     * Retrouver le film par rapport à l'ID envoyé en paraètre, on initialise l'ID
     * de la personne et on initialise la personne par rapport à l'ID
     */
    public void initMovie() {
        String idParam = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("id");
        if (!(idParam == null || idParam.isEmpty())) {
            currentMovieID = new Long(idParam);
            currentMovie = service.getMovieWithId(currentMovieID);
        }
    }

    /**
     * On met à jour le nom et le producteur du film
     * @return La navigation vers la page accueil
     */
    public String update() {
        service.getMovieWithId(currentMovieID).setName(currentMovie.getName());
        service.getMovieWithId(currentMovieID).setProducer(currentMovie.getProducer());
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * On ajoute le film courrant dans notre liste
     * @return La navigation vers la page accueil
     * @throws NullParameterException 
     */
    public String add() throws NullParameterException {
        currentMovie.setPeople(new ArrayList<Person>());
        service.saveMovie(currentMovie);
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * On vide les attributs du film
     * @return La navigation vers la même page en envoyant l'ID duf film en paramètre
     */
    public String reset() {
        this.currentMovie.setName("");
        this.currentMovie.setProducer("");
        return "edit.xhtml?faces-redirect=true&id=" + currentMovieID;
    }

    /**
     * On envoie un paramètre destination qui va nous permettre de définir la navigation
     * @param dest destination
     * @return La navigation à exécuter
     */
    public String nav(String dest) {
        if (dest.equals("editMovie")) {
            return "edit.xhtml?faces-redirect=true&id=" + currentMovieID;
        } else if (dest.equals("accueil")) {
            return "/index.xhtml";
        } else if (dest.equals("addMovie")) {
            return "movies/edit.xhtml";
        } else {
            return "movieList.xhtml";
        }
    }
}
