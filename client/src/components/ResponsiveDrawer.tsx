import { useState } from "react";

import Box from "@mui/material/Box";
import ClickAwayListener from "@mui/material/ClickAwayListener";
import Divider from "@mui/material/Divider";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";

import MailIcon from "@mui/icons-material/Mail";
import PaidIcon from "@mui/icons-material/Paid";
import DashboardIcon from "@mui/icons-material/Dashboard";
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";
import GroupsIcon from "@mui/icons-material/Groups";
import SettingsIcon from "@mui/icons-material/Settings";
import InfoIcon from "@mui/icons-material/Info";

export interface IResponsiveDrawerProps {
  drawerWidth: number;
  isMobile: boolean;
}

const DRAWER_ITEMS = [
  {
    id: 1,
    menuName: "Home",
    menuIcon: <DashboardIcon color="primary" />,
    divider: false,
  },
  {
    id: 2,
    menuName: "Transaction",
    menuIcon: <PaidIcon color="primary" />,
    divider: false,
  },
  {
    id: 3,
    menuName: "My Accounts",
    menuIcon: <AccountBalanceIcon color="primary" />,
    divider: false,
  },
  {
    id: 4,
    menuName: "Beneficiary",
    menuIcon: <GroupsIcon color="primary" />,
    divider: true,
  },
  {
    id: 5,
    menuName: "Apply Online",
    menuIcon: <MailIcon color="secondary" />,
    divider: false,
  },
  {
    id: 6,
    menuName: "Settings",
    menuIcon: <SettingsIcon color="secondary" />,
    divider: false,
  },
  {
    id: 7,
    menuName: "About",
    menuIcon: <InfoIcon color="secondary" />,
    divider: false,
  },
];

const ResponsiveDrawer: React.FC<IResponsiveDrawerProps> = ({
  drawerWidth = 240,
  isMobile = true,
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleDrawerClose = () => {
    setIsOpen(false);
  };

  const drawer = (
    <Box>
      <Box>
        <Typography variant="h5" textAlign="center" p={2}>
          Hello
        </Typography>
      </Box>
      <Divider />
      <List>
        {DRAWER_ITEMS.map((item) => (
          <>
            <ListItem key={item.id} disablePadding>
              <ListItemButton>
                <ListItemIcon>{item.menuIcon}</ListItemIcon>
                <ListItemText primary={item.menuName} />
              </ListItemButton>
            </ListItem>
            {item.divider && <Divider sx={{ my: 2 }} />}
          </>
        ))}
      </List>
    </Box>
  );

  return (
    <Box component="nav">
      <Drawer
        variant={isMobile ? "temporary" : "permanent"}
        open={isOpen}
        onClose={handleDrawerClose}
        slotProps={{
          paper: {
            elevation: 2,
            sx: {
              boxSizing: "border-box",
              width: drawerWidth,
            },
          },
        }}
      >
        <ClickAwayListener onClickAway={() => setIsOpen(false)}>
          {drawer}
        </ClickAwayListener>
      </Drawer>
    </Box>
  );
};

export default ResponsiveDrawer;
