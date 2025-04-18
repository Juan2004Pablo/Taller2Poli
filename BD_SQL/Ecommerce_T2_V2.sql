USE [ECommerce_T2_V2]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[idCategory] [int] IDENTITY(1,1) NOT NULL,
	[Category] [varchar](255) NOT NULL,
	[Image] [varchar](255) NOT NULL,
	[Description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[idCategory] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Details]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Details](
	[idDetail] [bigint] IDENTITY(1,1) NOT NULL,
	[Status] [varchar](100) NOT NULL,
	[Create_at] [datetime] NOT NULL,
	[Identification] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Details_products]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Details_products](
	[idDetailsProducts] [int] IDENTITY(1,1) NOT NULL,
	[idDetail] [bigint] NULL,
	[idProduct] [bigint] NULL,
	[Quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetailsProducts] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[idOrder] [bigint] IDENTITY(1,1) NOT NULL,
	[Total] [bigint] NOT NULL,
	[Address] [varchar](255) NULL,
	[Create_at] [datetime] NOT NULL,
	[idDetail] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[idOrder] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Pays]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pays](
	[idPay] [int] IDENTITY(1,1) NOT NULL,
	[Reference] [varchar](10) NOT NULL,
	[Status] [varchar](255) NOT NULL,
	[RequestId] [bigint] NOT NULL,
	[Name] [varchar](255) NOT NULL,
	[Identification] [bigint] NOT NULL,
	[Email] [varchar](255) NOT NULL,
	[Phone] [bigint] NULL,
	[Payment_method] [varchar](255) NOT NULL,
	[Create_at] [datetime] NOT NULL,
	[idOrder] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[idPay] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[idProduct] [bigint] IDENTITY(1,1) NOT NULL,
	[idCategory] [int] NULL,
	[Name] [varchar](255) NOT NULL,
	[Description] [varchar](255) NULL,
	[Image] [varchar](255) NOT NULL,
	[Stock] [int] NOT NULL,
	[Price] [bigint] NOT NULL,
	[Created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[idProduct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 3/30/2025 2:32:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Identification] [bigint] NOT NULL,
	[Name] [varchar](255) NOT NULL,
	[Address] [varchar](80) NOT NULL,
	[Phone] [varchar](10) NOT NULL,
	[Email] [varchar](250) NOT NULL,
	[Password] [varchar](255) NULL,
	[Created_at] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Identification] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Pays__062B9EB8CBECC80B]    Script Date: 3/30/2025 2:32:18 PM ******/
ALTER TABLE [dbo].[Pays] ADD UNIQUE NONCLUSTERED 
(
	[Reference] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Pays__33A8517B469FCA45]    Script Date: 3/30/2025 2:32:18 PM ******/
ALTER TABLE [dbo].[Pays] ADD UNIQUE NONCLUSTERED 
(
	[RequestId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Details]  WITH CHECK ADD FOREIGN KEY([Identification])
REFERENCES [dbo].[Users] ([Identification])
GO
ALTER TABLE [dbo].[Details]  WITH CHECK ADD FOREIGN KEY([Identification])
REFERENCES [dbo].[Users] ([Identification])
GO
ALTER TABLE [dbo].[Details_products]  WITH CHECK ADD FOREIGN KEY([idDetail])
REFERENCES [dbo].[Details] ([idDetail])
GO
ALTER TABLE [dbo].[Details_products]  WITH CHECK ADD FOREIGN KEY([idProduct])
REFERENCES [dbo].[Products] ([idProduct])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([idDetail])
REFERENCES [dbo].[Details] ([idDetail])
GO
ALTER TABLE [dbo].[Pays]  WITH CHECK ADD FOREIGN KEY([idOrder])
REFERENCES [dbo].[Orders] ([idOrder])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([idCategory])
REFERENCES [dbo].[Category] ([idCategory])
GO
