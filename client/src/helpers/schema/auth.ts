import * as z from "zod";

export const adminLoginSchema = z.object({
  username: z.string().trim().nonempty("Username is required"),
  password: z.string().nonempty("Password is required"),
});

export type AdminLoginSchema = z.infer<typeof adminLoginSchema>;
