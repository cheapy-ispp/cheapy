<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<cheapy:layout pageName="home"> 
<form action='/charge' method='POST' id='checkout-form' xmlns:th="http://www.w3.org/1999/xhtml%22%3E">
			    <input type='hidden' th:value='${amount/100}' name='amount' />
			    <h1>Price:<span th:text='${amount/100}'></span></h1>
			    <script
		            src='https://checkout.stripe.com/checkout.js'
		            class='stripe-button'
		            th:attr='data-key=${stripePublicKey},
		         	data-amount=${amount}'
		            data-name='StackAbuse Services'
		            data-description='Product Checkout'
		            data-image
		                    ='http://www.stackabuse.com/assets/images/sa-java-dark.svg?v=b5a08453df'
		            data-locale='auto'
		            data-zip-code='false'>
   				</script>
			</form>
</cheapy:layout>