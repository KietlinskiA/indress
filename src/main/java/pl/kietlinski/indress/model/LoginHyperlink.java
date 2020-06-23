package pl.kietlinski.indress.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginHyperlink {

    private String hyperlink;
    private String loginButton;

}
