import axiosInstance from "./apiClient";

export const adminLogin = async (username: string, password: string) => {
  try {
    const response = await axiosInstance.post("/auth/admin-login", {
      username,
      password,
    });
    return response.data;
  } catch (error) {
    console.error("Error in adminLogin :: ", error);
    throw error;
  }
};
