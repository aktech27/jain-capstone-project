import CONFIG from "@/config/constants";
import axios from "axios";

const axiosInstance = axios.create({
  baseURL: CONFIG.API_URL,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.response.use(undefined, (error) => {
  if (error.response?.status === 401) {
    window.location.replace("/auth/login");
    return;
  }

  throw error;
});

export default axiosInstance;
