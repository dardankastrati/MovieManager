
package ch.hearc.ig.odi.moviemanager.converter;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.service.Services;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dardan.kastrati
 */
@Named(value = "movieLOVConverter")
@Dependent
public class MovieLOVConverter implements Converter {
    @Inject
    Services service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        } else {
            for (Movie currentMovie : service.getMoviesList()) {
                if (currentMovie.getName().equals(value)) {
                    return currentMovie;
                }
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Movie) {
            return ((Movie) value).getName();
        } else {
            return "";
        }
    }
}
