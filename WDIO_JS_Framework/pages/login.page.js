const elementUtil = require('../util/element.util')

class LoginPage{

    //page locators
    get emailInput() {return $("//input[@id='identifierId']")}
    get nextButton() {return $("//button[contains(.,'Next')]")}
    get passwordInput() {return $("//input[@name='password']")}
    get validationMessageEle() {return $(`//div[@class='o6cuMc'][contains(.,"Couldn't find your Google Account")]`)}
    get welcomeText() {return $("//h1[contains(.,'Welcome')]")} 

    //Page actions:
    passValueInEmailInput(email){
        elementUtil.doSetValue(this.emailInput, email)
    }
    
    isNextButtonEnable(){
       return elementUtil.doIsEnabled(this.nextButton)
    }

    clickOnNextButton(){
        elementUtil.doClick(this.nextButton)
    }

    passValueInPasswordInput(password){
        elementUtil.doSetValue(this.passwordInput, password)
    }

    getInvalidValidationText(){

        return elementUtil.doGetText(this.validationMessageEle)
        // browser.waitUntil(function(){
        //     
        // } ,10000, 'validation text doesnt displayed in given time') 
    }

    getLoginPageLandingText(){
        return elementUtil.doIsDisplayed(this.welcomeText)
    }

}

module.exports = new LoginPage()