import * as z from "zod";

export const adminLoginSchema = z.object({
  email: z.email(),
  password: z.string().nonempty("Password is required"),
});

export type AdminLoginSchema = z.infer<typeof adminLoginSchema>;
