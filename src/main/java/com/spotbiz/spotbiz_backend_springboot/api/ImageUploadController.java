package com.spotbiz.spotbiz_backend_springboot.api;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadController {

    @PostMapping("/api/v1/upload_image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://freeimage.host/api/1/upload";

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("source", file.getResource());
            body.add("action", "upload");
            body.add("key", "6d207e02198a847aa98d0a2a901485a5");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                String imageUrl = jsonResponse.getJSONObject("image").getString("url");

                return ResponseEntity.ok(imageUrl);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload image");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }
}
