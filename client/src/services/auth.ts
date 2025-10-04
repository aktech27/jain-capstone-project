import axiosInstance from "./apiClient";
import API_ENDPOINTS from "./apiEndpoints";

import type { AxiosError, AxiosResponse } from "axios";
import type {
  AdminLoginRequestPayload,
  AdminLoginResponse,
} from "@/types/service/auth";

export const adminLogin = async (username: string, password: string) => {
  try {
    const response = await axiosInstance.post<
      AdminLoginResponse,
      AxiosResponse<AdminLoginResponse>,
      AdminLoginRequestPayload
    >(API_ENDPOINTS.AUTH.ADMIN_LOGIN, {
      username,
      password,
    });
    if (response.status == 200) {
      return {
        isSuccessful: true,
        message: "Login Successful",
        data: response?.data?.data,
      };
    }
    return {
      isSuccessful: true,
      message: response?.data?.message,
      data: response?.data?.data,
    };
  } catch (error) {
    if ((error as AxiosError).response?.status == 401) {
      return {
        isSuccessful: false,
        message: "Invalid Credentials",
      };
    }
  }
};
