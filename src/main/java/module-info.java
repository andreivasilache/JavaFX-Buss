module main.easybussro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;

    opens main.easybussro.controllers to javafx.fxml;

    exports main.easybussro;
}