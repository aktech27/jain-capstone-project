import { GenericResponse } from ".";

export interface AdminLoginRequestPayload {
  username: string;
  password: string;
}

export interface AdminLoginUserDetails {
  id: number;
  username: string;
  email: string;
  name: string;
}

export interface AdminLoginResponseData {
  token: string;
  userDetails: AdminLoginUserDetails;
}

export type AdminLoginResponse =
  | GenericResponse<AdminLoginResponseData>
  | GenericResponse<null>;
