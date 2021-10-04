package org.perfumepedia.DataBase.component;

import org.springframework.stereotype.Component;

@Component
public class HtmlTagGenerator {
    public String getScoreStar(Double score) {
        String star = "";
        if (score >= 0 && score < 0.5) {
            star += "<i class=\"far fa-star\"></i>";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 0.5 && score < 1) {
            star += "<i class=\"fas fa-star-half-alt\"></i>";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 1 && score < 1.5) {
            star = "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 1.5 && score < 2) {
            star = "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star-half-alt\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 2 && score < 2.5) {
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 2.5 && score < 3) {
            star += "<i class=\"fas fa-star\"></i>";
            star += "<i class=\"fas fa-star\"></i>";
            star += "<i class=\"fas fa-star-half-alt\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 3 && score < 3.5) {
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 3.5 && score < 4) {
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star-half-alt\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 4 && score < 4.5) {
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"far fa-star\"></i> ";
            return star;
        }
        else if (score >= 4.5 && score == 5) {
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i> ";
            star += "<i class=\"fas fa-star\"></i>";
            return star;
        }
        return star;
    }
}
