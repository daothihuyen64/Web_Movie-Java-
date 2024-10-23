
package com.webxemphim.demo.dto;

//import java.util.List;

public class SimpleGenreDTO {
    private int id;
    private String genreName;
    //private List<SimpleMovieDTO> movieList; // Sử dụng SimpleMovieDTO để trả về danh sách phim đơn giản
    public SimpleGenreDTO() {}
    public SimpleGenreDTO(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }


}
