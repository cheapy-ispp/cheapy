<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="usuarios">
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%;   padding:10px">
        <fmt:message key="usuario"/>
    </h2>
    
    <form:form modelAttribute="usuario" class="form-horizontal" id="add-usuario-form">
        <div class="form-group has-feedback">
            <cheapy:inputField label="Nombre" name="nombre"/>
            <cheapy:inputField label="Apellidos" name="apellidos"/>
            <cheapy:inputField label="Email" name="email"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<div class="text-left">
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
	                        
	                        <a href="/usuarios/edit/password">
	                        <button class="btn btn-default" type="button"  style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Cambiar contraseña</button></a>
                </div>
            </div>
        </div>
    </form:form>
    	<script>
	    
			function myFunction() {
				
				
				  var x = document.getElementById("myInput");
				  if (x.type === "password") {
				    x.type = "text";
				    $( "#showPassword" ).prop( "checked", true );
				  } else {
				    x.type = "password";
				    $( "#showPassword" ).prop( "checked", false );
				  }
				}
	
		</script>
    
</cheapy:layout>
