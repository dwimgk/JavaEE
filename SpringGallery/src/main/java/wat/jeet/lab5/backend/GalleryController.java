package wat.jeet.lab5.backend;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class GalleryController {

    @GetMapping("/random-image")
    public ResponseEntity<byte[]> getRandomImage() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://picsum.photos/800/600";

        byte[] image = restTemplate.getForObject(url, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
