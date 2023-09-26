package example.domain.content.service;

import example.domain.content.entity.Content;
import example.domain.content.repository.ContentRepository;
import example.global.S3.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final S3Upload s3Upload;

    public void createContent(Integer storeid, Long userid, List<MultipartFile>files, Integer rating, String contenttext) throws IOException {
        Content content=new Content();
        content.setStoreId(storeid);
        content.setUserId(userid);
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String storedFileName = s3Upload.upload(file,"images");
            imageUrls.add(storedFileName);
        }
        content.setImageUrls(imageUrls);
        content.setRating(rating);
        content.setContentText(contenttext);
        contentRepository.save(content);
    }
}
