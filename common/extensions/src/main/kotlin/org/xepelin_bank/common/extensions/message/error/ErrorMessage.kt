package org.xepelin_bank.common.extensions.message.error

object ErrorMessage {
    const val LICENSE_NOT_FOUND = "License not found"
    const val ORGANIZATION_ID_NOT_FOUND = "Organization not found"
    const val USER_ID_NOT_FOUND = "User not found"
    const val PROJECT_ID_NOT_FOUND = "Project not found"
    const val PROPOSAL_DOCUMENT_ID_NOT_FOUND = "Proposal document not found"
    const val FILE_NOT_FOUND = "File not found"
    const val CV_NOT_FOUND = "Curriculum not found"
    
    const val ERROR_WHEN_SENDING_AUTH = "Error when is sending authentication, make sure to send the token"
    const val ERROR_WHEN_CREATING_USER = "Error when is creating user"
    const val ERROR_WHEN_CREATING_ORGANIZATION = "Error when is creating organization"
    const val ERROR_WHEN_TAKING_OPTION = "Error when is taking a option of create or update user"
    const val ERROR_WHEN_SENDING_EMAIL = "Error when is sending email when tried to get organization"
    const val ERROR_WHEN_TRYING_CREATE_PROJECT = "Error when is trying of create a project"
    const val ERROR_WHEN_TRYING_OBJECT = "Error when is trying a cast json to object"
    const val ERROR_WHEN_TRYING_USE_BUCKET = "Error when is trying to use bucket"
    const val ERROR_WHEN_TRYING_DELETE_PROJECT =
        "Error when is trying to delete this project because has proposal document"
    const val ERROR_WHEN_SENDING_IDENTIFIER_CV = "Error when is sending identifier of curriculum vitae"
    const val ERROR_WHEN_TRYING_TO_REMOVE_RELATION_WITH_CV =
        "Error when tried to remove relation with curriculum vitae to the project"
    const val ERROR_WHEN_TRYING_GET_ORGANIZATION_CLIENT = "Error when tried to get enable organization"
    
    const val ERROR_FLOW_ORGANIZATION_NOT_CREATED = "Organization is not created"
    
    const val ERROR_PERMISSION = "The user does not have permission to view this information"
    
    const val ERROR_DUPLICATE = "%s duplicate"
}
