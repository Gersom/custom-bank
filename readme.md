# CustomBank 🏦

> A custom bank plugin for your Minecraft server! Create your own economy with personalized currency. 💰

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Spigot](https://img.shields.io/badge/spigot-1.20+-green.svg)
![License](https://img.shields.io/badge/license-MIT-yellow.svg)

## ❤️ Support the Project

If you find this plugin helpful, consider supporting its development:

[![PayPal](https://img.shields.io/badge/PayPal-Donate-blue.svg?logo=paypal)](https://paypal.me/gersomalaja)

Your support helps maintain and improve CustomBank! 🙏

## 📝 Description

CustomBank is a powerful and flexible banking plugin that allows players to manage their in-game currency with ease. Whether you want to withdraw money as physical items, check balances, or manage the server's economy, CustomBank has got you covered!

## ✨ Features

- 💎 Custom currency name and symbol
- 💰 Withdraw money as physical items
- 📊 Check account balance
- 🏆 View server's richest players ranking
- 🌍 Multi-language support (English, Spanish)
- 🔒 Permission-based command system
- ⚡ Vault integration for economy management

## 🔧 Requirements

- Minecraft Server 1.20+
- [Vault](https://www.spigotmc.org/resources/vault.34315/) plugin
- Any Vault-compatible economy plugin (e.g., EssentialsX)

## 📥 Installation

1. Download the latest version of CustomBank
2. Place the JAR file in your server's `plugins` folder
3. Start/restart your server
4. Configure the plugin in the `config.yml` file

## ⚙️ Configuration

```yaml
# Prefix of the plugin
prefix: "[Bank]" 

# Plugin language [en, es, other]
language: "en"

# Currency settings
coin:
  name: "GerCoins"
  symbol: "⛃"
```

## 📚 Commands

### Player Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/bank withdraw <amount>` | Withdraw money as physical item | `custombank.use` |
| `/bank balance` | Check your balance | `custombank.use` |
| `/bank ranking` | View richest players | `custombank.use` |
| `/bank author` | Show plugin author | `custombank.use` |

### Admin Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/bank givemoney <player> <amount>` | Give money to a player | `custombank.admin` |
| `/bank setmoney <player> <amount>` | Set player's balance | `custombank.admin` |
| `/bank reducemoney <player> <amount>` | Reduce player's balance | `custombank.admin` |
| `/bank reload` | Reload configuration | `custombank.admin` |
| `/bank version` | Show plugin version | `custombank.admin` |
| `/bank help` | Show help menu | `custombank.admin` |

## 🔒 Permissions

- `custombank.use` - Access to basic commands
- `custombank.admin` - Access to administrative commands

## 🌍 Languages

Currently supported languages:
- 🇺🇸 English (en)
- 🇪🇸 Spanish (es)
- ➕ Add your own language in the `lang` folder!

## 🤝 Contributing

Feel free to:
- Report bugs
- Suggest new features
- Create pull requests
- Add new language translations

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Created with 💖 by Gersom

## 💬 Support

If you have any questions or need help, feel free to:
- Create an issue
- Contact the author
- Check the [wiki](https://github.com/yourusername/CustomBank/wiki) (coming soon)
- Join our growing community!

## 💝 Donate

If you'd like to support the development of CustomBank:
- PayPal: [Donate Here](https://paypal.me/gersomalaja)

Your donations help keep the project alive and enable new features! Thank you for your support! 🙏