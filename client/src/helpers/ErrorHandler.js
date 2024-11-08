export default {
    handleError(error, action = "processing the request") {
        // Check if error response is available and show relevant message to user
        if (error.response) {
            const status = error.response.status;
            let errorMessage;

            if (status === 400) {
                errorMessage = `Invalid data provided. Please check your inputs while ${action}.`;
            } else if(status === 403){
                errorMessage = "Access restricted; You must be logged in as an admin for this!";
            } else if (status === 404) {
                errorMessage = `Resource not found while ${action}. It may have been removed.`;
            } else if (status === 500) {
                errorMessage = `Server error occurred while ${action}. Please try again later.`;
            } else {
                errorMessage = `An error occurred while ${action}. Please try again.`;
            }

            window.alert(errorMessage);
        } else if (error.request) {
            // Request was made but no response was received
            window.alert('Network error. Please check your connection');
        } else {
            // Something else happened in setting up the request
            window.alert(`An unexpected error occurred while ${action}. Please try again.`);
        }
    }
}