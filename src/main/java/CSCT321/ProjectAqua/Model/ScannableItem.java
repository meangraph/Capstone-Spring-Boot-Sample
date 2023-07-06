package CSCT321.ProjectAqua.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ScannableItem {

    private String itemName;
    private String description;
    private String barcode;

}
