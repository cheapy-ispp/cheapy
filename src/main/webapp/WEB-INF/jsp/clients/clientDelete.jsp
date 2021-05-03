<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="client">

	<jsp:body>
        <h2 class="titulo" style="font-family: 'Lobster'; font-size: 25px;  padding:30px">¿Está seguro de que quiere eliminar su cuenta? 
        Sus ofertas dejarán de ser visibles para los clientes, solo quedarán en nuestro registro interno con el nombre de su local y los demás datos se eliminarán del sistema.</h2>
      		
      		<form:form modelAttribute="client" class="form-horizontal">
            
				      <div class="eliminar" style="float:none">
        			<button type="submit" >
        			<span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
        			Eliminar cuenta</button>
        		</div>
        	</form:form>
        
    </jsp:body>
    
</cheapy:layout>
