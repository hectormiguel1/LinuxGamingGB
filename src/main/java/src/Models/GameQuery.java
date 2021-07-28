package src.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record GameQuery(String searchQuery, List<String> tags) {
    public String getSearchQuery() {
        return searchQuery;
    }
     public List<String> getTags() {
         return Objects.requireNonNullElseGet(tags, ArrayList::new);
     }
}
