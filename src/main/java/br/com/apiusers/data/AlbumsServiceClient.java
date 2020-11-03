package br.com.apiusers.data;

import br.com.apiusers.ui.controllers.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albumss")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
