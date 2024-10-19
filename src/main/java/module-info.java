module br.com.etec.aula20240906 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens br.com.etec.aula20240906 to javafx.fxml;
    exports br.com.etec.aula20240906;
    exports br.com.etec.aula20240906.model;
    opens br.com.etec.aula20240906.model to javafx.fxml;
}