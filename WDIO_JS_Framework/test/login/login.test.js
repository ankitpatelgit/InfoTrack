const loginPage = require('../../pages/login.page')
const configData = require('../../config')
const { assert } = require('chai')

describe('Login suite', function(){

    //this.retries(2) //this helps to excute the test for secondtime if it gets failed at first time
    it('Verify if a user can not log in with an invalid username and valid password.', function(){
        
        browser.url('/')
        browser.maximizeWindow()

        loginPage.passValueInEmailInput(configData.invalidEmail)

        assert.equal(true, loginPage.isNextButtonEnable(), 'Next button is not enabled.')

        loginPage.clickOnNextButton()

        assert.equal(configData.validationMessage, loginPage.getInvalidValidationText(), 'Invalid email validation message is not appeared.')

        browser.pause(5000)
        
    })
    
    it('Verify if a user able to login with a valid username and valid password.', function(){
        browser.url('/')
        browser.maximizeWindow()
        
        
        loginPage.passValueInEmailInput(configData.validEmail)

        assert.equal(true, loginPage.isNextButtonEnable(), 'Next button is not enabled.')

        loginPage.clickOnNextButton()

        loginPage.passValueInPasswordInput(configData.password)

        assert.equal(true, loginPage.isNextButtonEnable(), 'Next button is not enabled.')

        loginPage.clickOnNextButton()

        assert.equal(true, loginPage.getLoginPageLandingText(), 'User is not able login successfully with valid creds.')

        browser.pause(5000)

    })

})
