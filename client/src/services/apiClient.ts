import CONFIG from "@/config/constants";
import axios from "axios";
import API_ENDPOINTS from "./apiEndpoints";

const axiosInstance = axios.create({
  baseURL: CONFIG.API_URL,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      if (error?.config?.url == API_ENDPOINTS.AUTH.ADMIN_LOGIN) {
        return Promise.reject(error);
      }
      window.location.replace("/auth/login");
      return;
    }

    return Promise.reject(error);
  }
);

export default axiosInstance;
