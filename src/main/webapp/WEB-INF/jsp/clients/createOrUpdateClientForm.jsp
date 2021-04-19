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
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px">
        <fmt:message key="client"/>
    </h2>
    
    <form:form modelAttribute="client" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
			                
            <cheapy:passwordField label="Contraseña" placeholder="Restaurante pepito" name="usuar.password"/>		
            <cheapy:inputField label="Hora de inicio" placeholder="HH:mm" name="init"/>
            <cheapy:inputField label="Hora de fin" placeholder="HH:mm" name="finish"/>
            <cheapy:inputField label="Nombre" placeholder="Restaurante pepito" name="name"/>
            <cheapy:inputField label="Email" placeholder="" name="email"/>
            <cheapy:inputField label="Dirección" placeholder="" name="address"/>           
            <cheapy:inputField label="Teléfono" placeholder="" name="telephone"/>
            <cheapy:inputField label="Descripción" placeholder="" name="description"/>
            <cheapy:inputField label="Comida" placeholder="food" name="food"/>
            <cheapy:selectMunicipio label="Municipio" name="municipio" size="1" items="${municipios}" ></cheapy:selectMunicipio>
            
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
            	<div class="btn-mod">
	               
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
                </div>
            </div>
        </div>
    </form:form>
    
</cheapy:layout>
