<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="speedOffers">
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px">
        <c:if test="${speedOffer['new']}"><fmt:message key="new"/> </c:if> <fmt:message key="speedOffer"/>
    </h2>
    
    <form:form modelAttribute="speedOffer" class="form-horizontal" id="add-speedOffer-form">
        <div class="form-group has-feedback">
            <form:hidden path="id"/>
            <form:hidden path="code"/>
            <form:hidden path="status"/>
            <cheapy:dateField label="Fecha de Inicio" placeholder="dd/MM/yyyy HH:mm" name="start"/>
            <cheapy:dateField label="Fecha de Fin"  placeholder="dd/MM/yyyy HH:mm" name="end"/>
            <c:if test="${gold != null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Oro)" value="${gold}" name="gold"/></c:if>
            <c:if test="${gold == null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Oro)" value="00:00:00" name="gold"/></c:if>
            <cheapy:inputField label="Descuento nivel Oro" placeholder="XX% (Ej. 35)" name="discountGold"/>
            <c:if test="${silver != null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Plata)" value="${silver}" name="silver"/></c:if>
            <c:if test="${silver == null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Plata)" value="00:00:00" name="silver"/></c:if>
            
            <cheapy:inputField label="Descuento nivel Plata" placeholder="XX% (Ej. 15)" name="discountSilver"/>
            <c:if test="${bronze != null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Bronce)" value="${bronze}" name="bronze"/></c:if>
            <c:if test="${bronze == null  }"><cheapy:timeSecondsField label="Tiempo para comer (nivel Bronce)" value="00:00:00" name="bronze"/></c:if>
            <cheapy:inputField label="Descuento nivel Bronce" placeholder="XX% (Ej. 5)" name="discountBronze"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<div class="btn-mod">
	                <c:choose>
	                    <c:when test="${speedOffer['new']}">
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Crear oferta</button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
	                    </c:otherwise>
	                </c:choose>
                </div>
            </div>
        </div>
    </form:form>
   
	
</cheapy:layout>
