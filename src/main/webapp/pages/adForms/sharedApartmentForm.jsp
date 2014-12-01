<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-sm-9">
<form:form method="post" modelAttribute="shApForm" action="/viewAd" id="shApForm" cssClass="form-horizontal"  autocomplete="off" >
    <form:hidden path="category" value="Shared Apartment"/>
	<form:hidden path="id"/>
	
	<div class="large">
    <c:set var="titleErrors"><form:errors path="title"/></c:set>
    <div class="control-group<c:if test="${not empty titleErrors}"> error</c:if>">
      	<label class="control-label" for="field-title">Title</label>
        <div class="controls">
            <form:input path="title" id="field-title" class="wide-input" tabindex="1" maxlength="75" placeholder="Title"/>
            <form:errors path="title" cssClass="help-inline" element="span"/>
        </div>
    </div>
    </div>
</div>
</div> 
<div class="row">
<div class="col-sm-4">  
    <fieldset>
		<legend>Location</legend>
        <c:set var="streetErrors"><form:errors path="street"/></c:set>
        <div class="control-group<c:if test="${not empty streetErrors}"> error</c:if>">
            <label class="control-label" for="field-street">Street</label>
            <div class="controls">
                <form:input path="street" id="field-street" tabindex="2" maxlength="50" placeholder="Street"/>
                <form:errors path="street" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="numberErrors"><form:errors path="number"/></c:set>
        <div class="control-group<c:if test="${not empty numberErrors}"> error </c:if>">
            <label class="control-label" for="field-number">Nr.</label>
            <div class="controls">
                <form:input path="number" id="field-number" tabindex="3" maxlength="5"/>
                <form:errors path="number" cssClass="help-inline" element="span"/>
            </div>
        </div>
		<c:set var="zipCodeErrors"><form:errors path="zipCode"/></c:set>
        <div class="control-group<c:if test="${not empty zipCodeErrors}"> error</c:if>">
            <label class="control-label" for="field-zipCode">ZIP-Code</label>
            <div class="controls">
                <form:input path="zipCode" id="field-zipCode" tabindex="4" maxlength="5"/>
                <form:errors path="zipCode" cssClass="help-inline" element="span"/>
            </div>
        </div>
		<c:set var="cityErrors"><form:errors path="city"/></c:set>
        <div class="control-group<c:if test="${not empty cityErrors}"> error</c:if>">
            <label class="control-label" for="field-city">City</label>
            <div class="controls">
                <form:input path="city" id="field-city" tabindex="5" maxlength="35" placeholder="City"/>
                <form:errors path="city" cssClass="help-inline" element="span"/>
            </div>
        </div>
</div>        
<div class="col-sm-4"> 
		<legend>Environment</legend> 
		<h3><small>(distances in meter)</small></h3>     
		<c:set var="distanceToPubTrErrors"><form:errors path="distanceToPubTr"/></c:set>
        <div class="control-group<c:if test="${not empty distanceToPubTrErrors}"> error</c:if>">
            <label class="control-label" for="field-distanceToPubTr">Distance to public transport</label>
            <div class="controls">
                <form:input path="distanceToPubTr" id="field-distanceToPubTr" tabindex="6" maxlength="5" placeholder="meter"/>
                <form:errors path="distanceToPubTr" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="distanceToShopErrors"><form:errors path="distanceToShop"/></c:set>
        <div class="control-group<c:if test="${not empty distanceToShopErrors}"> error</c:if>">
            <label class="control-label" for="field-distanceToShop">Distance to shops</label>
            <div class="controls">
                <form:input path="distanceToShop" id="field-distanceToShop" tabindex="7" maxlength="5" placeholder="meter"/>
                <form:errors path="distanceToShop" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="distanceToParkErrors"><form:errors path="distanceToPark"/></c:set>
        <div class="control-group<c:if test="${not empty distanceToParkErrors}"> error</c:if>">
            <label class="control-label" for="field-distanceToPark">Distance to park</label>
            <div class="controls">
                <form:input path="distanceToPark" id="field-distanceToPark" tabindex="8" maxlength="5" placeholder="meter"/>
                <form:errors path="distanceToPark" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="distanceToSchoolErrors"><form:errors path="distanceToSchool"/></c:set>
        <div class="control-group<c:if test="${not empty distanceToSchoolErrors}"> error</c:if>">
            <label class="control-label" for="field-distanceToSchool">Distance to School</label>
            <div class="controls">
                <form:input path="distanceToSchool" id="field-distanceToSchool" tabindex="9" maxlength="5" placeholder="meter"/>
                <form:errors path="distanceToSchool" cssClass="help-inline" element="span"/>
            </div>
        </div>
</div>
<div class="col-sm-4">
		<legend>Rent Details</legend>
		<c:set var="priceErrors"><form:errors path="price"/></c:set>
        <div class="control-group<c:if test="${not empty priceErrors}"> error</c:if>">
            <label class="control-label" for="field-price">Price (CHF)</label>
            <div class="controls">
                <form:input path="price" id="field-price" tabindex="6" maxlength="5" placeholder="chf" />
                <form:errors path="price" cssClass="help-inline" element="span"/>
            </div>
        </div>
		
		<div class="control-group">
			<label  class="control-label"  for="field-fixedMoveIn">Fixed move-in date </label>
			
			<div class="controls">
				<form:checkbox id="field-fixedMoveIn" tabindex="7" path="fixedMoveIn" />
				
			</div>
		</div>
		
		<c:set var="moveInErrors"><form:errors path="moveIn"/></c:set>
        <div id="moveInJS" class="control-group<c:if test="${not empty moveInErrors}"> error</c:if>">
            <label class="control-label" for="field-moveIn">Move-in date</label>
            <div class="controls">
            	<fmt:formatDate pattern="dd/MM/yyyy" value="${shApForm.moveIn}" var="simpleInDate"/>
                <form:input path="moveIn" id="field-moveIn" tabindex="8" maxlength="10" value="${simpleInDate}" placeholder="dd/MM/yyyy"  />
                <form:errors path="moveIn" cssClass="help-inline" element="span"/>
            </div>
        </div>
		
		<div class="control-group">
			<label  class="control-label"  for="field-fixedMoveOut">Fixed move-out date </label>
			
			<div class="controls">
				<form:checkbox id="field-fixedMoveOut" tabindex="9" path="fixedMoveOut" />
				
			</div>
		</div>
		
		<c:set var="moveOutErrors"><form:errors path="moveOut"/></c:set>
        <div id="moveOutJS" class="control-group<c:if test="${not empty moveOutErrors}"> error</c:if>">
            <label class="control-label" for="field-moveOut">Move-out date</label>
            <div class="controls">
            	<fmt:formatDate type="date" pattern="dd/MM/yyyy" value="${shApForm.moveOut}" var="simpleOutDate"/>
                <form:input path="moveOut" id="field-moveOut" tabindex="10" maxlength="10" value="${simpleOutDate}" placeholder="dd/MM/yyyy" />
                <form:errors path="moveOut" cssClass="help-inline" element="span"/>
            </div>
        </div>
</div>
</div>
<div class="row">
<div class="col-sm-6">
		<legend>Apartment Details</legend>

		
		<c:set var="sizeErrors"><form:errors path="roomSize"/></c:set>
        <div class="control-group<c:if test="${not empty sizeErrors}"> error</c:if>">
            <label class="control-label" for="field-size">Room size (m<sup>2</sup>)</label>
            <div class="controls">
                <form:input path="roomSize" id="field-size" tabindex="12" maxlength="5"  />
                <form:errors path="roomSize" cssClass="help-inline" element="span"/>
            </div>
        </div>
		
		<c:set var="descriptionErrors"><form:errors path="description"/></c:set>
        <div class="control-group<c:if test="${not empty descriptionErrors}"> error</c:if>">
            <label class="control-label" for="field-description">Description</label>
            <div class="controls">
                <form:textarea path="description" id="field-description" tabindex="13" rows="10" class="wide-input"/>
                <form:errors path="description" cssClass="help-inline" element="span"/>
            </div>
        </div>
        </fieldset>
</div>
<div class="col-sm-6">
        <fieldset>
        <legend>Choose the tags describing your apartment</legend>
        <div class="text-center">
        <form:hidden path="tags.smokingAllowed"  id="field-smokingAllowed"/>
        <div class="btn <c:if test="${!shApForm.tags.smokingAllowed}">btn-default</c:if><c:if test="${shApForm.tags.smokingAllowed}">btn-green</c:if>" id="smokingAllowed" onclick="setTag(this.id)">Smoking Allowed</div>
        <form:hidden path="tags.petsAllowed"  id="field-petsAllowed"/>
        <div class="btn <c:if test="${!shApForm.tags.petsAllowed}">btn-default</c:if><c:if test="${shApForm.tags.petsAllowed}">btn-green</c:if>" id="petsAllowed" onclick="setTag(this.id)">Pets Allowed</div>
        <form:hidden path="tags.musicInstrumentsAllowed"  id="field-musicInstrumentsAllowed"/>
        <div class="btn <c:if test="${!shApForm.tags.musicInstrumentsAllowed}">btn-default</c:if><c:if test="${shApForm.tags.musicInstrumentsAllowed}">btn-green</c:if>" id="musicInstrumentsAllowed" onclick="setTag(this.id)">Musical Instruments Allowed</div>
        <form:hidden path="tags.bikeParking"  id="field-bikeParking"/>
        <div class="btn <c:if test="${!shApForm.tags.bikeParking}">btn-default</c:if><c:if test="${shApForm.tags.bikeParking}">btn-green</c:if>" id="bikeParking" onclick="setTag(this.id)">Bike-parking</div>
        <form:hidden path="tags.carParking"  id="field-carParking"/>
        <div class="btn <c:if test="${!shApForm.tags.carParking}">btn-default</c:if><c:if test="${shApForm.tags.carParking}">btn-green</c:if>" id="carParking" onclick="setTag(this.id)">Car-parking</div>
        <form:hidden path="tags.sharedGarden"  id="field-sharedGarden"/>
        <div class="btn <c:if test="${!shApForm.tags.sharedGarden}">btn-default</c:if><c:if test="${shApForm.tags.sharedGarden}">btn-green</c:if>" id="sharedGarden" onclick="setTag(this.id)">Shared Garden</div>
        <form:hidden path="tags.balcony"  id="field-balcony"/>
        <div class="btn <c:if test="${!shApForm.tags.balcony}">btn-default</c:if><c:if test="${shApForm.tags.balcony}">btn-green</c:if>" id="balcony" onclick="setTag(this.id)">Balcony</div>
        <form:hidden path="tags.quietNeighbourhood"  id="field-quietNeighbourhood"/>
        <div class="btn <c:if test="${!shApForm.tags.quietNeighbourhood}">btn-default</c:if><c:if test="${shApForm.tags.quietNeighbourhood}">btn-green</c:if>" id="quietNeighbourhood" onclick="setTag(this.id)">Quiet Neighbourhood</div>
        <form:hidden path="tags.elevator"  id="field-elevator"/>
        <div class="btn <c:if test="${!shApForm.tags.elevator}">btn-default</c:if><c:if test="${shApForm.tags.elevator}">btn-green</c:if>" id="elevator" onclick="setTag(this.id)">Elevator</div>
        <form:hidden path="tags.wheelchairAccessible"  id="field-wheelchairAccessible"/>
        <div class="btn <c:if test="${!shApForm.tags.wheelchairAccessible}">btn-default</c:if><c:if test="${shApForm.tags.wheelchairAccessible}">btn-green</c:if>" id="wheelchairAccessible" onclick="setTag(this.id)">Wheelchair Accessible</div>
        <form:hidden path="tags.lowEnergyBuilding" id="field-lowEnergyBuilding"/>
        <div class="btn <c:if test="${!shApForm.tags.lowEnergyBuilding}">btn-default</c:if><c:if test="${shApForm.tags.lowEnergyBuilding}">btn-green</c:if>" id="lowEnergyBuilding" onclick="setTag(this.id)">Low Energy Building</div>
        
        <form:hidden path="tags.eatingCookingTogether"  id="field-eatingCookingTogether"/>
        <div class="btn <c:if test="${!shApForm.tags.eatingCookingTogether}">btn-default</c:if><c:if test="${shApForm.tags.eatingCookingTogether}">btn-green</c:if>" id="eatingCookingTogether" onclick="setTag(this.id)">Eating/Cooking Together</div>
        <form:hidden path="tags.stayingWeekends" id="field-stayingWeekends"/>
        <div class="btn <c:if test="${!shApForm.tags.stayingWeekends}">btn-default</c:if><c:if test="${shApForm.tags.stayingWeekends}">btn-green</c:if>" id="stayingWeekends" onclick="setTag(this.id)">Staying Weekends</div>
        <form:hidden path="tags.vegetarianVegan" id="field-vegetarianVegan"/>
        <div class="btn <c:if test="${!shApForm.tags.vegetarianVegan}">btn-default</c:if><c:if test="${shApForm.tags.vegetarianVegan}">btn-green</c:if>" id="vegetarianVegan" onclick="setTag(this.id)">Vegetarian/Vegan</div>
        <form:hidden path="tags.nonVegetarian" id="field-nonVegetarian"/>
        <div class="btn <c:if test="${!shApForm.tags.nonVegetarian}">btn-default</c:if><c:if test="${shApForm.tags.nonVegetarian}">btn-green</c:if>" id="nonVegetarian" onclick="setTag(this.id)">Non-vegetarian</div>
               
        </div>
</div>
</div>
<div class="row">
<div class="col-sm-6">
        <c:import url="roomMateTable.jsp" />

        <form:hidden id="field-addRoomMate" path="addRoomMate" value="false"/>
        <button type="button" id="RoomMateButton" class="btn btn-primary">add Roommate</button> 
</div>
</div>
<div class="row">
<div class="col-sm-6"></div>
<div class="col-sm-6 text-right">

        <div class="form-actions ">
            <button type="submit" class="btn btn-green btn-lg">Submit Ad</button>
            <button type="button" class="btn btn-grey btn-lg">Cancel</button>
        </div>
    </fieldset>
</form:form>
</div>
</div>
<script>
document.getElementById('RoomMateButton').onclick = function(){
	document.getElementById('field-addRoomMate').value = 'true';
	document.getElementById('shApForm').submit();
	
}
</script>
<script>
	function setTag(elementId){
		if( document.getElementById('field-' + elementId).value == 'false'){
			document.getElementById('field-' + elementId).value = 'true';
			document.getElementById(elementId).className = 'btn btn-green';		
		}
		else{
			document.getElementById('field-' + elementId).value = 'false';
			document.getElementById(elementId).className = 'btn btn-default';
		}
		
	}
</script>