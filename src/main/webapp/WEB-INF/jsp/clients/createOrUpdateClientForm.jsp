<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="clients">
    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%;  padding:10px">
        <fmt:message key="client"/>
    </h2>
    
    <form:form modelAttribute="client" class="form-horizontal" enctype="multipart/form-data" id="add-client-form">
        <div class="form-group has-feedback">
        	<form:hidden path="expiration"/>
            <form:hidden path="preguntaSegura1"/>
            <form:hidden path="preguntaSegura2"/>
        
			<cheapy:inputField label="Nombre" placeholder="Restaurante pepito" name="name"/>                
            <cheapy:inputField label="Dirección" placeholder="" name="address"/>
            <cheapy:inputField label="¿Posee fácil aparcamiento?" placeholder="" name="parking"/>		
            <cheapy:timeField label="Hora de inicio" placeholder="HH:mm" name="init"/>
            <cheapy:timeField label="Hora de fin" placeholder="HH:mm" name="finish"/>
            <cheapy:selectMunicipio label="Municipio" name="municipio" size="1" items="${municipios}" ></cheapy:selectMunicipio>
            <cheapy:inputField label="Email" placeholder="" name="email"/>                 
            <cheapy:inputField label="Teléfono" placeholder="" name="telephone"/>
            <cheapy:inputField label="Descripción" placeholder="" name="description"/>
            <cheapy:inputField label="Comida" placeholder="" name="food"/>
          	<cheapy:inputField label="Imagen" placeholder="Copiar la url de una imagen aquí" name="image"/>
            
					<script>

						function setSelectedIndex(s, valsearch)
						
						{
						
							// Loop through all the items in drop down list
							
							for (i = 0; i< s.options.length; i++)
							
							{ 
							
								if (s.options[i].value == valsearch)
								
								{
								
								// Item is found. Set its property and exit
								
								s.options[i].selected = true;
								
								break;
								
								}
							
							}
						
						return;
						
						}
						
						setSelectedIndex(document.getElementById("selectMunicipio"),"${municipio}");
						
					</script>
	
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<div class="text-left">
	               
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
	                        <a href="/clients/edit/password">
	                        <button class="btn btn-default" type="button"  style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
	                        Cambiar contraseña</button></a>
	                       
	                        
	                        
                </div>
                 <button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
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
